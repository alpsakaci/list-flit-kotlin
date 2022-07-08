package com.alpsakaci.listflit.application.util

import kotlin.math.ceil
import kotlin.math.min


class Partition<T> (
    private var list: List<T>,
    private var chunkSize: Int,
    override var size: Int=0,
) : AbstractList<List<T>>() {

    init {
        this.size = size()
    }

    companion object {
        fun ofSize(list: List<Any>, chunkSize: Int): Partition<Any> {
            return Partition(list, chunkSize, 0)
        }
    }

    private fun size(): Int {
        return ceil(list.size.toDouble() / chunkSize.toDouble()).toInt()
    }

    override fun get(index: Int): List<T> {
        val start = index * chunkSize
        val end = min(start + chunkSize, list.size)

        if (start > end) {
            throw IndexOutOfBoundsException("Index " + index + " is out of the list range <0," + (size() - 1) + ">")
        }

        return ArrayList<T>(list.subList(start, end))
    }

}