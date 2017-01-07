package com.soywiz.korio.vfs

val localVfsProvider: LocalVfsProvider by lazy {
	throw UnsupportedOperationException("LocalVfsProvider not defined")
}

val urlVfsProvider: UrlVfsProvider by lazy {
	throw UnsupportedOperationException("UrlVfsProvider not defined")
}

val resourcesVfsProvider: ResourcesVfsProvider by lazy {
	throw UnsupportedOperationException("ResourcesVfsProvider not defined")
}