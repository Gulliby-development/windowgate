<p align="center"><img src="docs/logo.svg" width="96" alt="" /></p>

# windowgate

[![CI](https://github.com/Gulliby-development/windowgate/actions/workflows/ci.yml/badge.svg)](https://github.com/Gulliby-development/windowgate/actions/workflows/ci.yml) [![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

Offline Kotlin/JVM validator/FIFO scheduler for agents, batch jobs, terminals and CI. No API/network calls.

JDK 17+, Windows/macOS/Linux, Gradle.
Build: `gradle installDist`; run `build/install/windowgate/bin/windowgate COMMAND` (`windowgate.bat` on Windows).
Development: `gradle check`.

```text
usage: windowgate check [--rpm N] [--tpm N]
       windowgate plan [--rpm N] [--tpm N]
       windowgate waits [--rpm N] [--tpm N]
       windowgate stats [--rpm N] [--tpm N]
```
RPM: 1–60, default 10. TPM: 1–9999, default 1000.
ASCII digits only; no signs/leading zeros.
Only valid invocations read stdin.
`help`, `-h`, `--help`: usage on stdout, status 0.
`--version`: `windowgate 0.1.0`, status 0.
Both ignore trailing arguments; no stdin.
No arguments: stderr usage, exit 2.

Validate left to right: empty words, `-`, non-dash words are unexpected; unrecognized dash words are unknown options. Options consume any next word. Precedence: duplicate, missing, bad value. CLI errors (status 2):
```text
windowgate: unknown command WORD
windowgate: unexpected argument WORD
windowgate: unknown option WORD
windowgate: given twice: OPTION
windowgate: missing value for OPTION
windowgate: bad value for OPTION
```
Case-sensitive; other first words (even empty) are unknown commands.

`ID TIME TOKENS`, separated by spaces/tabs; trim spaces/tabs at ends.
Unique ID: 1–8 characters, `[a-z][a-z0-9_]*`.
TIME: 0–86399, nondecreasing (ties allowed).
TOKENS: 1–9999 and at most TPM.
Same numeric syntax; TIME allows `0`.

Split at LF; final unterminated line counts, terminal LF adds no phantom line. Empty input: zero lines. Strip one trailing CR.
Skip space/tab-only lines or first word starting `#`; no inline comments.
Count skipped lines. First check: over 40 physical lines gives `windowgate: input is too long (max 40 lines)`.

Check requests in order. Error: `windowgate: line N: REASON`, N counts physical lines from 1. Ordered reasons:
1. `expected ID TIME TOKENS`
2. `bad id WORD`
3. `bad time WORD`
4. `bad tokens WORD`
5. `duplicate id ID`
6. `time goes back`
7. `tokens exceed --tpm`

Input errors exit 1. Success: exit 0, stdout only. Errors: no stdout/partial report, one stderr line (except usage). ASCII text; output lines end in LF.

Earliest whole-second FIFO send S: not before arrival/previous send. Window S-59..S includes simultaneous sends cumulatively; S-60 expires. Existing count < RPM and tokens plus new tokens <= TPM. Wait = send-arrival; sends may exceed 86399.

- `check`: validation only; `ok N requests`, singular `ok 1 request`.
- `plan`: `ID arrive T send S wait W` per request in input order; footer `last send S`.
- `waits`: `ID waited W s` for positive waits in input order; footer `delayed K of N`.
- `stats`: seven lines in order: `requests N`, `delayed K`, `wait total X`, `wait max Y`, `last send S`, `peak requests Q`, `peak tokens P`.

N=requests; K=positive waits; X=sum waits; Y=max wait. Q/P=independent window peaks at sends, new sends included.
Empty/skipped input: check `ok 0 requests`; plan `last send 0`; waits `delayed 0 of 0`; all seven stats values zero.


POSIX examples: repository directory, after installation; define
`windowgate() { build/install/windowgate/bin/windowgate "$@"; }`.
`2>&1` shows stderr.
```text
$ printf 'a1 0 100\na2 5 100\na3 10 100\n' | windowgate plan --rpm 2
a1 arrive 0 send 0 wait 0
a2 arrive 5 send 5 wait 0
a3 arrive 10 send 60 wait 50
last send 60
$ printf 'a 0 600\nb 1 600\nc 2 100\n' | windowgate waits
b waited 59 s
c waited 58 s
delayed 2 of 3
$ printf 'a 0 100\nb 30 100\nc 61 100\n' | windowgate stats --rpm 2
requests 3
delayed 0
wait total 0
wait max 0
last send 61
peak requests 2
peak tokens 200
$ printf 'a 0 100\nb 0 x\n' | windowgate check 2>&1; printf 'a 0 100\n' | windowgate plan --tpm 10000 2>&1
windowgate: line 2: bad tokens x
windowgate: bad value for --tpm
```
MIT: see LICENSE.

---

Built with [BAKED](https://github.com/Gulliby-development/baked), an open-source coding agent (bench round R95).
