REM @echo off
REM ============================================================================
REM タイトル：テーブル再編成処理
REM 説明    :テーブル再編成を行う処理です。
REM version 1.00 2010/05/24 K.SHIBUYA:新規作成
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat


%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB191010
IF %ERRORLEVEL% NEQ 0 GOTO error

:end

CD %BATCHF_HOME%

EXIT 0

:error

EXIT 1