/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package org.eclipse.kura.usb;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Representation of USB TTY devices
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ProviderType
public class UsbTtyDevice extends AbstractUsbDevice {

    /** The device node of the TTY device **/
    private final String deviceNode;
    private final Integer interfaceNumber;

    public UsbTtyDevice(String vendorId, String productId, String manufacturerName, String productName,
            String usbBusNumber, String usbDevicePath, String deviceNode) {
        super(vendorId, productId, manufacturerName, productName, usbBusNumber, usbDevicePath);
        this.deviceNode = deviceNode;
        this.interfaceNumber = null;
    }

    public UsbTtyDevice(String vendorId, String productId, String manufacturerName, String productName,
            String usbBusNumber, String usbDevicePath, String deviceNode, Integer interfaceNumber) {
        super(vendorId, productId, manufacturerName, productName, usbBusNumber, usbDevicePath);
        this.deviceNode = deviceNode;
        this.interfaceNumber = interfaceNumber;
    }

    public String getDeviceNode() {
        return this.deviceNode;
    }

    public Integer getInterfaceNumber() {
        return this.interfaceNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((deviceNode == null) ? 0 : deviceNode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsbTtyDevice other = (UsbTtyDevice) obj;
        if (deviceNode == null) {
            if (other.deviceNode != null)
                return false;
        } else if (!deviceNode.equals(other.deviceNode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UsbTtyDevice [getDeviceNode()=" + getDeviceNode() + ", getVendorId()=" + getVendorId()
                + ", getProductId()=" + getProductId() + ", getManufacturerName()=" + getManufacturerName()
                + ", getProductName()=" + getProductName() + ", getUsbBusNumber()=" + getUsbBusNumber()
                + ", getUsbDevicePath()=" + getUsbDevicePath() + ", getUsbPort()=" + getUsbPort()
                + ", getInterfaceNumber()=" + getInterfaceNumber() + "]";
    }
}
