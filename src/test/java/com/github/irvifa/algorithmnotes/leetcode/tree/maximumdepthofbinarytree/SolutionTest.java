package com.github.irvifa.algorithmnotes.leetcode.tree.maximumdepthofbinarytree;

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
  public void getMaximumDepth() throws Exception {
    String input = "3,9,20,null,null,15,7";
    TreeNode root = TreeUtil.stringToTreeNode(input);
    int maximumDepth = underTest.maxDepth(root);
    assertThat(maximumDepth, is(equalTo(3)));
  }

}
