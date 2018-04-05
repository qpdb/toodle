/* -*- Mode: Java; c-basic-offset: 4; tab-width: 20; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.mozilla.toodle;

import android.content.Context;

import com.mozilla.toodle.rust.NativeItem;
import com.mozilla.toodle.rust.Toodle;
import com.mozilla.toodle.rust.TypedValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class Item {
    private long id;
    private UUID uuid;
    private String name;
    private Long dueDate;
    private Long completionDate;

    public String name() {
        return name;
    }

    Item name(final String name) {
        this.name = name;
        return this;
    }

    Item() {

    }

    public Item(long id, UUID uuid, String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }

    public UUID uuid() {
        return uuid;
    }

    public Long dueDate() {
        if(this.dueDate == null) {
            TypedValue value = Toodle.getSharedInstance(null).valueForAttributeOnEntity(":todo/due_date", this.id);
            if(value != null) {
                this.dueDate = value.asDate().getTime();
            }
        }
        return this.dueDate;
    }

    public Long completionDate() {
        if(this.completionDate == null) {
            TypedValue value = Toodle.getSharedInstance(null).valueForAttributeOnEntity(":todo/completion_date", this.id);
            if(value != null) {
                this.completionDate = value.asDate().getTime();
            }
        }
        return this.completionDate;
    }

    Item completionDate(Long timestamp) {
        if (timestamp == null) {
            completionDate = null;
        } else {
            completionDate = timestamp / 1000;
        }
        return this;
    }

    Item dueDate(final int year, final int month, final int date) {
        final Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);
        dueDate = cal.getTimeInMillis() / 1000;
        return this;
    }

    private static Item fromNativeItem(NativeItem nativeItem) {
        final Item item = new Item();
        item.uuid = UUID.fromString(nativeItem.uuid);
        item.name = nativeItem.itemName;
        if (nativeItem.dueDate != null) {
            item.dueDate = nativeItem.dueDate.getValue().longValue();
            if (item.dueDate == 0) {
                item.dueDate = null;
            }
        }
        if (nativeItem.completionDate != null) {
            item.completionDate = nativeItem.completionDate.getValue().longValue();
            if (item.completionDate == 0) {
                item.completionDate = null;
            }
        }
        return item;
    }

    static ArrayList<Item> fromNativeItems(List<NativeItem> nativeItems) {
        final ArrayList<Item> items = new ArrayList<>(nativeItems.size());

        for (NativeItem nativeItem : nativeItems) {
            items.add(fromNativeItem(nativeItem));
        }

        return items;
    }

    void create(Context context) {
        Toodle.getSharedInstance(context).createItem(this);
    }

    void update(Context context) {
        Toodle.getSharedInstance(context).updateItem(this);
    }
}