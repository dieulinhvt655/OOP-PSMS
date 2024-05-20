package System;

import java.io.IOException;

public class AppSystem{
  public static void clearScreen() {
    String os = System.getProperty("os.name").toLowerCase();
    try {
      if (os.contains("win")) {
        // Xóa màn hình trong Windows
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("aix")) {
        // Xóa màn hình trong Unix/Linux/Mac OS
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      } else {
        System.out.println("Hệ điều hành không được hỗ trợ để xóa màn hình");
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.println("Bạn sẽ thấy thông báo này trong 5 giây trước khi màn hình được xóa...");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    clearScreen();
    System.out.println("Màn hình đã được xóa.");
  }
}
