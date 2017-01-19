/*
 * Copyright 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.classyshark.silverghost.translator.apk.dashboard.manifest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReceiverActionsBL {

    static List<String> approvedActions = Arrays.asList(
            "android.intent.action.BOOT_COMPLETED",
            "com.google.android.c2dm.intent.REGISTRATION",
            "com.google.android.c2dm.intent.RECEIVE"
    );
    private final Map<String, String> actionsToReceivers;
    private final Map<String, String> bgActionsToReceivers;


    public ReceiverActionsBL(Map<String, String> actionsToReceivers) {
        this.actionsToReceivers = actionsToReceivers;
        this.bgActionsToReceivers = filterBGActions(actionsToReceivers);
    }

    public List<String> getBGActionsList() {
        List<String> result = new LinkedList<>();

        for (Map.Entry<String, String> entry : bgActionsToReceivers.entrySet()) {
            result.add("* System action " + entry.getKey() + " at receiver "+ entry.getValue() + "\n");
        }
        return result;
    }

    private Map<String, String> filterBGActions(Map<String, String> actions) {
        TreeMap<String, String> result = new TreeMap<>();

        for (Map.Entry<String, String> entry : actions.entrySet()) {
            if (!approvedActions.contains(entry.getKey())) {
                if (entry.getKey().startsWith("com.google.") ||
                        entry.getKey().startsWith("android.")) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }
}