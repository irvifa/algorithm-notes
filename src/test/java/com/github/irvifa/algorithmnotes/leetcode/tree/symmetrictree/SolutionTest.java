package com.github.irvifa.algorithmnotes.leetcode.tree.symmetrictree;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeUtil;
import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest {
  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void isSymmetricTree() throws Exception {
    String input = "1,2,2,3,4,4,3";
    TreeNode root = TreeUtil.stringToTreeNode(input);
    boolean result = underTest.isSymmetric(root);
    assertThat(result, is(equalTo(true)));
  }
}
