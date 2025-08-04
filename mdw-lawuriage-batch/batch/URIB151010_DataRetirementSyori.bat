REM @echo off
REM ============================================================================
REM タイトル	：データ退役処理
REM 説明	：各種データの退役処理を実施する。
REM version 1.00 2009/05/27 L.Cheng:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== データ退役処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB151010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1

