REM @echo off
REM ============================================================================
REM タイトル	：新単品明細ログ（差分）　FTP送信処理
REM 説明	：オンメモリサーバーにFTP送信する。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
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

REM ===== 新単品明細ログ（差分）　FTP送信処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB171030
IF %ERRORLEVEL% NEQ 0 goto CHECK

:END

CD %BATCHF_HOME%

exit 0

:ERROR

exit 1


