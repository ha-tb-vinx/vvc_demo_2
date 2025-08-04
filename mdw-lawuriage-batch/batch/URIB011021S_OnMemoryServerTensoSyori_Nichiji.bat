REM @echo off
REM ============================================================================
REM タイトル	：オンメモリサーバー転送処理(日次商品)
REM 説明	：NCR-POSデータファイルをNCR側サーバーの指定フォルダから取得し、基幹側サーバーの各ファイル毎の指定フォルダにコピーする。
REM version 1.00 2009/07/02 VJC:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

SET COUNT=0

:CHECK
SET /A COUNT=%COUNT% + 1
REM 10秒待って5回リトライ
IF %COUNT%.==6. goto ERROR
IF %COUNT% NEQ 1 ping localhost -n 10
ECHO %COUNT%回目の試行を実行します。

REM ===== オンメモリサーバー転送処理(日次商品)
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB011021S
IF %ERRORLEVEL% NEQ 0 goto CHECK

:END

CD %BATCHF_HOME%

exit 0

:ERROR

exit 1

