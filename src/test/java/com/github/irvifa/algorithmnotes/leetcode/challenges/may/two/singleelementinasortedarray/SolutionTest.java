package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.singleelementinasortedarray;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SolutionTest {

  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void getSingleElementInSortedArray() {
    assertThat(underTest.singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8}), is(equalTo(2)));
  }

}
