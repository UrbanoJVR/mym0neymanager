package com.urbanojvr.spendcontrolandroidapp.domain

import java.util.*

class Transaction(
    var id: Long,
    var concept: String,
    var amount: Double,
    var date: Date
) {

}