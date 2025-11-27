{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk21          # Java 21
    maven          # Maven 3.9.x
    kotlin         # Kotlin
    docker-compose # For compose.yaml
  ];

  # Set JAVA_HOME for tools that need it
  JAVA_HOME = "${pkgs.jdk21}";
}
