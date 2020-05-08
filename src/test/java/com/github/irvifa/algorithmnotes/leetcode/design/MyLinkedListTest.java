package com.github.irvifa.algorithmnotes.leetcode.design;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MyLinkedListTest {

  private MyLinkedList underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new MyLinkedList();
  }

  @Test
  public void performValidOperation() throws Exception {
    underTest.addAtHead(1);
    assertThat(underTest.get(underTest.getSize() - 1), is(equalTo(1)));
    underTest.addAtTail(3);
    assertThat(underTest.get(underTest.getSize() - 1), is(equalTo(3)));
    underTest.addAtIndex(1, 2);
    assertThat(underTest.get(1), is(equalTo(2)));
    underTest.deleteAtIndex(1);
    assertThat(underTest.get(1), is(equalTo(3)));
  }
}
