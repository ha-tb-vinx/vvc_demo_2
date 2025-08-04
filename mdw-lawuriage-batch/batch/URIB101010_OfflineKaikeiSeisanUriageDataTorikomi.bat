REM @echo off
REM ============================================================================
REM タイトル	：オフライン会計精算売上データ取込処理
REM 説明	：オフライン会計精算売上データ取込処理
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== オフライン会計精算売上データ取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB101010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1