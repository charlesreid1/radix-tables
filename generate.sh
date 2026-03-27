#!/usr/bin/env bash
# generate.sh - Compile and run all table generators.
# Generates HTML files into docs/ for GitHub Pages.

set -e
cd "$(dirname "$0")"

rm -rf build
mkdir -p build

echo "=== Compiling and running generators ==="
for gen in Times Primes Powers Factorials Reciprocals Harshad Persistence; do
  echo "  $gen..."
  javac -d build "src/${gen}.java"
  java -cp build "$gen"
done

echo "=== Done. HTML files written to docs/ ==="
