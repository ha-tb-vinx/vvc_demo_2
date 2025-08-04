REM @echo off
REM ----------------------------------------------------------------------------
REM  BATCH-ID    : URIB013350
REM  CRATOR      : T.KAMEI
REM  CREATE DATE : 2016/07/12
REM ----------------------------------------------------------------------------
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@
CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB013350
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
