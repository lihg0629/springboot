package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * メインクラス
 * @author lihuaguang
 *
 */
@SpringBootApplication//これは大切のアノテーション
public class Application {

	// メインメソッド
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
