package com.sophia.saveandshowmyactivitytoday

import com.sophia.saveandshowmyactivitytoday.entity.Check

interface CheckListData {
    fun checkList(content: String, date: String)
}