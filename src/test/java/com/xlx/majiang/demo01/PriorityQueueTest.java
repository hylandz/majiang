package com.xlx.majiang.demo01;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * 优先级队列测试
 *
 * @author xielx on 2019/8/12
 */
public class PriorityQueueTest {


	@Test
	public void testPriorityQueue(){
		PriorityQueue<String> priorityQueue = new PriorityQueue<>();

		PriorityQueue<String> q = new PriorityQueue<String>();
		//入列
		q.offer("1");
		q.offer("2");
		q.offer("5");
		q.offer("3");
		q.offer("4");

		//出列poll:获取并移除头
		System.out.println(q.poll());  //1
		System.out.println(q.poll());  //2
		System.out.println(q.poll());  //3
		System.out.println(q.poll());  //4
		System.out.println(q.poll());  //5

	}
}
