# Radix Tables

Mathematical tables in every number base, from binary to vigesimal.

**[Browse the tables live](https://charlesreid1.github.io/radix-tables)**

## What is this?

We count in base 10 — probably because we have ten fingers. But there is nothing mathematically special about ten. Every number base has its own version of the times table, its own way of writing primes, its own patterns hiding in the digits.

This project generates large, browsable HTML tables that let you explore familiar mathematical sequences in unfamiliar number bases (radix 2 through 20). The same numbers, seen from a different angle.

## The tables

**Times Tables** — 50 x 50 multiplication tables. Palindromic products are highlighted in red. You can watch the diagonal (perfect squares) and spot symmetries that change shape from base to base.

**Prime Numbers** — The first 5,000 primes, laid out in a grid. Primes are the same in every base, but their digit patterns are not. Palindromic primes are highlighted in red; twin primes (pairs separated by 2) are highlighted in blue.

**Powers of Integers** — Each integer raised to the 1st through 10th power, plus triangular numbers. Watching how powers grow digit-by-digit reveals the internal structure of each base.

**Factorials** — Factorials from 1! to 100!, which grow enormous fast. Seeing 100! written in base 3 is an experience.

## How it works

Four standalone Java programs — one per table type — use `BigInteger` to perform arbitrary-precision arithmetic and convert results into any base. Each program writes a set of HTML files (one per base) into the `docs/` directory, which is served directly by GitHub Pages.

## Building

Requires Java (JDK 8+).

```
./generate.sh
```

This compiles all generators and writes fresh HTML into `docs/`.

## Project structure

```
src/              Java source for the four table generators
docs/             Generated HTML, CSS, and JS (GitHub Pages root)
experiments/      Archived earlier work (base-10 utilities, SPA prototype)
generate.sh       Build script
```

## License

MIT
