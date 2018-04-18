package com.doubler.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.doubler.board.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private static int contentNumber = 0;
	
	@Autowired
	private BoardService boardService;
}
