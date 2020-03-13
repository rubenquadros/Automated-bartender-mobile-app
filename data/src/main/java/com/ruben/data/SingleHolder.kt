package com.ruben.data

/**
 * Created by ruben.quadros on 12/03/20.
 **/
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
  private var creator: ((A) -> T)? = creator
  @Volatile
  private var instance: T? = null

  public fun getInstance(arg: A): T {
    val i = instance
    if (i != null) return i
    return synchronized(this) {
      val i2 = instance
      if (i2 != null) {
        return i2
      } else {
        val created = creator!!(arg)
        instance = created
        creator = null
        created
      }
    }
  }
}