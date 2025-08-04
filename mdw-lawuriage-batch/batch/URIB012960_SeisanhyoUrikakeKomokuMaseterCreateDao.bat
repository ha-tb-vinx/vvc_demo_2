REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB012960
REM  CRATOR      : N.Katou
REM  CREATE DATE : 2017/03/09
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012960
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1