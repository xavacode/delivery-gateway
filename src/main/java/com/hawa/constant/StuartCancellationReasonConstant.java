package com.hawa.constant;

public enum StuartCancellationReasonConstant {
    ADDRESS_ERROR("Address Error"),
    COURIER_ISSUE("Courier Issue"),
    CUSTOMER_CANCELLATION_REQUESTED("Customer Cancellation Requested"),
    DUPLICATE_PACKAGE("Duplicate Package"),
    INCORRECT_PACKAGE("Incorrect Package"),
    NO_SUPPLY("No Supply"),
    OTHER("Other"),
    PACKAGE_DAMAGED("Package Damaged"),
    PACKAGE_NOT_READY("Package Not Ready"),
    PU_CLOSED("PU Closed"),
    TECHNICAL_ISSUE("Technical Issue"),
    WRONG_PACKAGE_SIZE("Wrong Package Size");

    private final String displayName;

    StuartCancellationReasonConstant(String displayName) {
        this.displayName = displayName;
    }

    // Optional: Find enum by display name
    public static StuartCancellationReasonConstant fromDisplayName(String displayName) {
        for (StuartCancellationReasonConstant reason : values()) {
            if (reason.displayName.equals(displayName)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("Unknown Cancellation Reason: " + displayName);
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
