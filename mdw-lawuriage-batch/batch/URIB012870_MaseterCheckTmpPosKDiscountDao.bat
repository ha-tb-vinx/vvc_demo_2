REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB012870
REM  CRATOR      : J.Endo
REM  CREATE DATE : 2017/01/31
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012870
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
