REM @echo off
REM ============================================================================
REM タイトル	：単品明細差分取込処理DAO
REM 説明	：「リカバリ用新単品明細ログ」ファイルを「新単品明細ログ差分ワーク」へ挿入する。
REM version 1.00 2009/06/11 XP.Chen:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 単品明細差分取込処理DAO
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB171010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
