REM @echo off
REM ============================================================================
REM タイトル	：POS実績取込処理（レジ別取引精算Erec）
REM 説明	：サーバに格納されたファイルを使用してPOS実績取込処理（レジ別取引精算Erec）を実行する。
REM version 1.00 2017/04/27 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== POS実績取込処理（レジ別取引精算Erec）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012401
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
