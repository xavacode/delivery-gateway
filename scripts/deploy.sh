#!/bin/bash
# deploy.sh - Deploy application JAR

DROPLET_IP="$1"
APP_NAME="delivery-gateway"
JAR_FILE=$(ls target/*.jar | head -1)  # Get the built JAR

if [ -z "$JAR_FILE" ]; then
  echo "Error: No JAR file found!"
  exit 1
fi

echo "Deploying $JAR_FILE to $DROPLET_IP..."

# Stop service before deployment

ssh -o StrictHostKeyChecking=no -i ~/.ssh/id_rsa ${DEV_USER}@${DROPLET_IP} << EOF
"systemctl stop ${APP_NAME} || true"

# Copy new JAR
scp -o StrictHostKeyChecking=no "$JAR_FILE" ${DEV_USER}@${DROPLET_IP}:/opt/${APP_NAME}/app.jar

# Set permissions and restart
ssh -o StrictHostKeyChecking=no -i ~/.ssh/id_rsa ${DEV_USER}@${DROPLET_IP} << 'EOF'
  chown ${APP_NAME}:${APP_NAME} /opt/${APP_NAME}/app.jar
  chmod 500 /opt/${APP_NAME}/app.jar

  # Start service
  if systemctl start ${APP_NAME}; then
    echo "Service started successfully"
    sleep 3
    systemctl status ${APP_NAME} --no-pager
  else
    echo "Failed to start service"
    journalctl -u ${APP_NAME} -n 50 --no-pager
    exit 1
  fi
EOF

echo "Deployment completed!"