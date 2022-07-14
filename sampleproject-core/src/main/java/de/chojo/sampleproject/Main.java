/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) 2021 RainbowDashLabs and Contributor
 */

package de.chojo.sampleproject;

public class Main {
    public static void main(String[] args) {
        var apiClass = new ApiClassImpl();
        System.out.printf("Hello %s", apiClass.apiMethod(String.join(" ", args)));
    }
}
