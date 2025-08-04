REM @echo off
REM ============================================================================
REM タイトル：Ｆレコード履歴カットデータ作成
REM 説明    ：ＦレコードワークよりＦレコード履歴カットデータ作成
REM version 1.00 2017/08/08 N.Kato:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012406
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
