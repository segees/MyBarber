package com.example.test2

class DataSource {
    companion object {
        fun createDataSet(blogList:List<String>,count:String): ArrayList<BlogPost> {
            val now:Int=count.toInt()
            val list =  ArrayList<BlogPost>()
            for(item in 0 until  now){
                list.add(
                    BlogPost(
                        item.toLong(),
                        blogList[item],
                        blogList[item+now]+" ILS/30 min",
                        blogList[item+now*2],
                    )
                )
            }
            return list
        }
    }
}