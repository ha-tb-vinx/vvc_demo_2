@echo off
REM ============================================================================
REM タイトル：リリース確認処理（売上管理）
REM 説明	：リリースが正しくできているか判別する
REM version 3.00 2014/02/26 S.Arakawa:新規作成
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ======== リリース確認処理（売上管理）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB999999
IF %ERRORLEVEL% NEQ 0 GOTO error

:end

CD %BATCHF_HOME%

EXIT 0

:error

EXIT %ERRORLEVEL%