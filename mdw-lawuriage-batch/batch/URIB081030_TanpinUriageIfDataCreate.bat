REM @echo off
REM ============================================================================
REM タイトル	：単品売上IF用データ作成処理
REM 説明	：IF用データを抽出し、「IF単品別売上データ」を作成する。
REM version 1.00 2010/01/20 M.Tada:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 単品売上IF用データ作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB081030
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1