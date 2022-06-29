package com.example.test2

class ItemSource {
    companion object {
        fun createDataSet(itemList:List<String>, count:String): ArrayList<ItemsViewModel> {
            val now:Int=count.toInt()
            val list = ArrayList<ItemsViewModel>()
            for(item in 0 until  now){
                list.add(
                    ItemsViewModel(
                        item.toLong(),
                        itemList[item],
                        itemList[item+now],
                    )
                )
            }
            return list
        }
    }
}