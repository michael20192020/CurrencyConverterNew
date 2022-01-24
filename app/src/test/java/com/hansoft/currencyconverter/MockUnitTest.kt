package com.hansoft.currencyconverter

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MockUnitTest {

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    fun testList()
    {
        val mockedList: MutableList<String>? = mock(MutableList::class.java) as MutableList<String>?
        //using mock object
        mockedList?.add("one");
        println(mockedList?.size.toString())
        mockedList?.clear();
        println(mockedList?.size.toString())
        //verification
        verify(mockedList)?.add("one");
        verify(mockedList)?.clear();
    }

    @Test
    fun testInterface() {
        val myInterface: NetworkInterface = mock(NetworkInterface::class.java)

        `when`(myInterface.getString("https://www.westpac.com.au/bin/getJsonRates.wbc.fx.json")).thenReturn("abc")

        assertEquals(myInterface.getString("https://www.westpac.com.au/bin/getJsonRates.wbc.fx.json"), "abc")


        `when`(myInterface.getString("abc","123")).thenReturn("bcd")
        assertEquals(myInterface.getString("abc","123"), "bcd")

        `when`(myInterface.getString("abc","tom","123")).thenReturn("efg")
        assertEquals(myInterface.getString("abc","tom","123"), "efg")

    }

}

