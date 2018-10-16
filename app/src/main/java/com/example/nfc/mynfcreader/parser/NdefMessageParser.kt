/*
 * Copyright (C) 2010 The Android Open Source Project
 * Modified by Sylvain Saurel for a tutorial
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.nfc.mynfcreader.parser

// import android.app.Activity;
import android.nfc.NdefMessage
import android.nfc.NdefRecord

// import com.example.nfc.mynfcreader.R;
// import com.example.nfc.mynfcreader.model.History;
import com.example.nfc.mynfcreader.record.ParsedNdefRecord
import com.example.nfc.mynfcreader.record.SmartPoster
import com.example.nfc.mynfcreader.record.TextRecord
import com.example.nfc.mynfcreader.record.UriRecord
// import com.example.nfc.mynfcreader.utils.NFCReaderApp;

import java.util.ArrayList


/**
 * Data exchanged via NFC use the NDEF format. NDEF means NFC Data Exchange Format.
 */
object NdefMessageParser {

    fun parse(message: NdefMessage): List<ParsedNdefRecord> {
        return getRecords(message.records)
    }

    fun getRecords(records: Array<NdefRecord>): List<ParsedNdefRecord> {
        val elements = ArrayList<ParsedNdefRecord>()

        for (record in records) {
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record))
            } else if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record))
            } else if (SmartPoster.isPoster(record)) {
                elements.add(SmartPoster.parse(record))
            } else {
                elements.add(object : ParsedNdefRecord {
                    override fun str(): String {
                        return String(record.payload)
                    }
                })
            }
        }

        return elements
    }
}
