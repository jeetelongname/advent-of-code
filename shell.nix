{
  pkgs ? import <nixpkgs> { },
}:

pkgs.mkShell {
  buildInputs = with pkgs; [
    clojure
    clojure-lsp
    cljfmt
  ];
}
