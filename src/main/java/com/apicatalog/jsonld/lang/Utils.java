/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apicatalog.jsonld.lang;

import java.util.Collection;
import java.util.Collections;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

public final class Utils {

    private Utils() {
    }

    public static Collection<String> index(final Collection<String> keys, final boolean ordered) {

        if (keys == null) {
            return Collections.emptyList();
        }

        if (ordered) {
            if (keys.size() <= 1) {
                return keys;
            } else if (keys.size() == 2) {
                String[] array = keys.toArray(String[]::new);

                if (array[0].equals(Keywords.ID) && array[1].equals(Keywords.TYPE)) {
                    return ObjectList.of(Keywords.ID, Keywords.TYPE);
                } else if (array[1].equals(Keywords.VALUE)) {
                    if (array[0].equals(Keywords.LANGUAGE)) {
                        return ObjectList.of(Keywords.LANGUAGE, Keywords.VALUE);
                    } else if (array[0].equals(Keywords.TYPE)) {
                        return ObjectList.of(Keywords.TYPE, Keywords.VALUE);
                    }
                }

                if (array[0].compareTo(array[1]) <= 0) {
                    return ObjectList.of(array);
                } else {
                    return ObjectList.of(array[1], array[0]);
                }
            } else {
                if(keys.size() == 3){
                    String[] array = keys.toArray(String[]::new);
                    if (array[0].compareTo(array[1]) <= 0 && array[1].compareTo(array[2]) <= 0) {
                        return ObjectList.of(array);
                    }
                }

                ObjectList<String> sorted = new ObjectArrayList<>(keys);
                sorted.sort(String::compareTo);
                return sorted;
            }
        }
        return keys;
    }
}
