REM @echo off
REM ============================================================================
REM タイトル：単品精算データ作成
REM 説明    ：卸出荷伝票から単品精算データを作成する。
REM version 1.00 2016/11/17 T.Chihara:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB501010
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
