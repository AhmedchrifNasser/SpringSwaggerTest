package com.example.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/")
public class Controller {
    Stack<String> pile = new Stack<>();
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @GetMapping("/show")
    public ResponseEntity<Stack<String>> showPile(){
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @PutMapping ("/additem/{item}")
    public ResponseEntity<Stack<String>> addEelements(@PathVariable("item") String item){
        if (pattern.matcher(item).matches()) {
            pile.push(item);
        } else {
            System.out.println("put valid number");
        }
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<Stack<String>> emptyPile(){
        pile.clear();
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<Stack<String>> operandAdd(){
        pile.push("+");
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @PutMapping("/sub")
    public ResponseEntity<Stack<String>> operandSub(){
        pile.push("-");
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @PutMapping("/multi")
    public ResponseEntity<Stack<String>> operandMulti(){
        pile.push("*");
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @PutMapping("/div")
    public ResponseEntity<Stack<String>> operandDiv(){
        pile.push("/");
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    @GetMapping("/calculate")
    public ResponseEntity<Stack<String>> calculate(){
        if (pile.size()==3) {
              operate(pile);
        } else if (pile.size()>3) {
            Stack<String> temp = new Stack<String>();
            temp.add(pile.pop());
            String variable = pile.pop();
            while (!pattern.matcher(variable).matches()) {
                temp.add(variable);
                variable = pile.pop();
            }
            pile.push(variable);
            pile.push(temp.pop());
            operate(pile);
            for(String item:temp){
                pile.push(item);
            }
        }
        return new ResponseEntity<Stack<String>>(pile, HttpStatus.OK);
    }

    public Stack<String> operate(Stack<String> Pile){
        String operator = Pile.pop();
        Integer firstOperand = Integer.valueOf(Pile.pop());
        Integer SecondOperand = Integer.valueOf(Pile.pop());
        switch (operator) {
            case "+": Pile.push(String.valueOf(firstOperand+SecondOperand)) ;
                break;
            case "-": Pile.push(String.valueOf(firstOperand-SecondOperand)) ;
                break;
            case "*": Pile.push(String.valueOf(firstOperand*SecondOperand)) ;
                break;
            case "/": Pile.push(String.valueOf(firstOperand/SecondOperand)) ;
                break;
        }
        return Pile;
    }

}
