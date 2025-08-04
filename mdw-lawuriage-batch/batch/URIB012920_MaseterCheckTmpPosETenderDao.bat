REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB012920
REM  CRATOR      : k.Hyo
REM  CREATE DATE : 2016/10/31
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012920
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
