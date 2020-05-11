package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.cousinsinbinarytree;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import com.github.irvifa.algorithmnotes.leetcode.tree.TreeUtil;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest {

  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void areNotCousins() throws Exception {
    String input = "1,2,3,4";
    TreeNode root = TreeUtil.stringToTreeNode(input);
    assertThat(underTest.isCousins(root, 4, 3), is(equalTo(false)));
  }

  @Test
  public void areCousins() throws Exception {
    String input = "1,2,3,null,4,null,5";
    TreeNode root = TreeUtil.stringToTreeNode(input);
    assertThat(underTest.isCousins(root, 5, 4), is(equalTo(true)));
  }
}
