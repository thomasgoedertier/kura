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
package org.eclipse.kura.linux.usb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UsbSerial {

    private static final String FILENAME = "/proc/tty/driver/usbserial";

    private static ArrayList<UsbSerialEntry> s_entries = null;

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.kura.device.usb.usbmanager.usbserial.service.IUsbSerialService#getInfo()
     */
    private static void getInfo() throws Exception {

        BufferedReader in = new BufferedReader(new FileReader(FILENAME));
        s_entries = new ArrayList<UsbSerialEntry>();

        int ttyUsbNo = 0;
        String vendor = null;
        String product = null;
        int numPorts = 0;
        int portEnum = 0;
        String path = null;

        String line = in.readLine(); // read first line (do not need to parse it)
        while ((line = in.readLine()) != null) {

            ttyUsbNo = Integer.parseInt(line.substring(0, line.indexOf(':')));
            line = line.substring(line.indexOf("vendor:"));
            StringTokenizer st = new StringTokenizer(line, " ");
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (token.startsWith("vendor:")) {
                    vendor = token.substring(token.indexOf(":") + 1).trim();
                }
                if (token.startsWith("product:")) {
                    product = token.substring(token.indexOf(":") + 1).trim();
                }
                if (token.startsWith("num_ports:")) {
                    numPorts = Integer.parseInt(token.substring(token.indexOf(":") + 1).trim());
                }
                if (token.startsWith("port:")) {
                    portEnum = Integer.parseInt(token.substring(token.indexOf(":") + 1).trim());
                }
                if (token.startsWith("path:")) {
                    path = token.substring(token.indexOf(":") + 1).trim();
                }
            }
            s_entries.add(new UsbSerialEntry(ttyUsbNo, vendor, product, numPorts, portEnum, path));
        }
        in.close();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.kura.device.usb.usbmanager.usbserial.service.IUsbSerialService#getUsbSerialEntries(java.lang.String,
     * java.lang.String)
     */
    public static ArrayList<UsbSerialEntry> getUsbSerialEntries(String vendor, String product) throws Exception {

        getInfo();

        UsbSerialEntry entry = null;
        ArrayList<UsbSerialEntry> matches = new ArrayList<UsbSerialEntry>();

        for (int i = 0; i < s_entries.size(); i++) {
            entry = s_entries.get(i);
            if (entry.getVendorID().compareTo(vendor) == 0 && entry.getProductID().compareTo(product) == 0) {
                matches.add(entry);
            }
        }

        return matches;
    }
}
