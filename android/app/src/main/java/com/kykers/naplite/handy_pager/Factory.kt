package dashkudov.handy_pager

import androidx.paging.DataSource

internal class Factory<T>(private val builder: HandyPager.Builder<T>) :
        DataSource.Factory<Int, T>() {

        override fun create() = Source(builder)

    }