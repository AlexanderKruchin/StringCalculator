package com.example.stringcalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public static void main(String[] args) throws IOException {
        while (true){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String str = reader.readLine();

        String str2 = str.replaceAll("[~,%#$\\s.?\\]!\\[<`>^&{}@№'\"|;:_a-zA-Zа-яёА-ЯЁ]","");

        String[] sub = str2.split("=");
        String quest = sub[0];
        boolean correct = true;
        if (str.equals(str2)){
            System.out.println("Выражение корректно");
        }else {
            correct = false;
            System.out.println("Выражение не корректно, все лишнее удалено.");
        }

        if (sub.length==1){
            System.out.println("Вы не ввели предполагаемый ответ. Повторите ввод!");


        } else {
            String answer = sub[1];

            List<Token> tokens = tokenAnalyze(quest);

            TokenBuffer tokenBuffer = new TokenBuffer(tokens);
            int ans = expr(tokenBuffer);

            if (Integer.parseInt(answer)==ans){
                System.out.println("Грац");

            }else  System.out.println("Не грац");

            System.out.println("Правильный ответ:" + ans);
            System.out.println("Ваш ответ: " + answer);
            System.exit(0);
        }
      }
    }
    public static class Token {
        TokenType type;
        String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Token(TokenType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

    }

    public static class TokenBuffer {
        private int pos;

        public List<Token> tokens;

        public TokenBuffer(List<Token> tokens) {
            this.tokens = tokens;
        }

        public Token next() {
            return tokens.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    public static List<Token> tokenAnalyze(String expText) {
        ArrayList<Token> tokens = new ArrayList<>();
        int pos = 0;
        while (pos< expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    tokens.add(new Token(TokenType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    tokens.add(new Token(TokenType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    tokens.add(new Token(TokenType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    tokens.add(new Token(TokenType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    tokens.add(new Token(TokenType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    tokens.add(new Token(TokenType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        tokens.add(new Token(TokenType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Неправильный символ: " + c);
                        }
                        pos++;
                    }
            }
        }
        tokens.add(new Token(TokenType.END, ""));
        return tokens;
    }

    public static int expr(TokenBuffer tokens) {
        Token token = tokens.next();

        if (token.type == TokenType.END) {
            return 0;
        }

        else {
            tokens.back();
            return plusMinus(tokens);
        }

    }



    public static int plusMinus(TokenBuffer tokens) {
        int value = multiDiv(tokens);
        while (true) {
            Token token = tokens.next();
            switch (token.type) {
                case OP_PLUS:
                    value += multiDiv(tokens);
                    break;
                case OP_MINUS:
                    value -= multiDiv(tokens);
                    break;
                case END:
                case RIGHT_BRACKET:
                    tokens.back();
                    return value;
                default:
                    throw new RuntimeException("Неправильный символ: " + token.value
                            + " в позиции: " + tokens.getPos());
            }
        }
    }

    public static int multiDiv(TokenBuffer tokens) {
        int value = brackets(tokens);
        while (true) {
            Token token = tokens.next();
            switch (token.type) {
                case OP_MUL:
                    value *= brackets(tokens);
                    break;
                case OP_DIV:
                    value /= brackets(tokens);
                    break;
                case END:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    tokens.back();
                    return value;
                default:
                    throw new RuntimeException("Неправильный символ: " + token.value
                            + " в позиции: " + tokens.getPos());
            }
        }
    }

    public static int brackets(TokenBuffer tokens) {
        Token token = tokens.next();
        switch (token.type) {
            case NUMBER:
                return Integer.parseInt(token.value);
            case LEFT_BRACKET:
                int value = plusMinus(tokens);
                token = tokens.next();
                if (token.type != TokenType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + token.value
                            + " at position: " + tokens.getPos());
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + token.value
                        + " at position: " + tokens.getPos());
        }
    }
}
