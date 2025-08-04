REM @echo off
REM ============================================================================
REM タイトル	：売上外入金除外処理（単品点検）
REM 説明	：売上外入金除外処理（単品点検）を実行する。
REM version 3.00 2013/10/18 T.Ooshiro:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 売上外入金除外処理（単品点検）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB013110
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
