package com.soywiz.korio.vfs

import com.soywiz.korio.async.sync
import com.soywiz.korio.async.toList
import com.soywiz.korio.stream.openAsync
import org.junit.Assert
import org.junit.Test
import java.io.FileNotFoundException

class JailVfsTest {
	@Test
	fun name() = sync {
		val mem = MemoryVfs(mapOf(
			"hello/secret.txt" to "SECRET!".toByteArray().openAsync(),
			"hello/world/test.txt" to "HELLO WORLD!".toByteArray().openAsync()
		))

		Assert.assertEquals(
			"[hello, hello/secret.txt, hello/world, hello/world/test.txt]",
			mem.listRecursive().toList().map { it.fullname }.toString()
		)

		val worldFolder = mem["hello/world"]
		val worldFolderJail = mem["hello/world"].jail()

		Assert.assertEquals(
			"SECRET!",
			worldFolder["../secret.txt"].readString()
		)

		expectException<FileNotFoundException> {
			worldFolderJail["../secret.txt"].readString()
		}
	}

	inline fun <reified T : Throwable> expectException(callback: () -> Unit) {
		try {
			callback()
			Assert.fail()
		} catch (t: Throwable) {
			if (t is T) {
				Assert.assertTrue(true)
			} else {
				throw t
			}
		}
	}
}