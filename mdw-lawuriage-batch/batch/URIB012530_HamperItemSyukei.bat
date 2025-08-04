REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB012530
REM  CRATOR      : M.Kanno
REM  CREATE DATE : 2016/06/01
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012530
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
