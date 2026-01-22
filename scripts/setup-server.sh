#!/bin/bash
# setup-server.sh - One-time server configuration

DROPLET_IP="$1"
APP_NAME="delivery-gateway"

echo "Setting up server at $DROPLET_IP..."

ssh -o StrictHostKeyChecking=no -i ~/.ssh/id_rsa ${{ secrets.DEV_USER }}@${{ secrets.DEV_HOST }} << 'EOF'
  # Create application user (no login, system user)
  if ! id "$APP_NAME" &>/dev/null; then
    useradd -r -s /bin/false -m -d /opt/$APP_NAME $APP_NAME
    echo "Created user: $APP_NAME"
  fi

  # Create application directories
  mkdir -p /opt/$APP_NAME/{logs,config,backups}
  chown -R $APP_NAME:$APP_NAME /opt/$APP_NAME

  # Create systemd service file
  cat > /etc/systemd/system/$APP_NAME.service << 'SERVICE_EOF'
[Unit]
Description=Spring Boot Application
After=network.target

[Service]
User=$APP_NAME
WorkingDirectory=/opt/$APP_NAME
ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar app.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

# Logging
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
SERVICE_EOF

  # Replace APP_NAME placeholder in service file
  sed -i "s/\$APP_NAME/$APP_NAME/g" /etc/systemd/system/$APP_NAME.service

  # Reload systemd and enable service
  systemctl daemon-reload
  systemctl enable $APP_NAME

  # Setup firewall (allow SSH, HTTP, and app port)
  ufw allow 22/tcp
  ufw allow 8080/tcp
  ufw --force enable

  echo "Server setup completed!"
EOF