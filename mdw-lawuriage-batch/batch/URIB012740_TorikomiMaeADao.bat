REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB012740
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/09/29
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012740
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
