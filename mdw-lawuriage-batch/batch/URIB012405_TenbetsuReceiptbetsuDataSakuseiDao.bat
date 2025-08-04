REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB012405
REM  CRATOR      : X.Liu
REM  CREATE DATE : 2017/05/22
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012405
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
