REM @echo off
REM ============================================================================
REM タイトル	：KSPSP用商品マスタファイル生成処理
REM 説明	：KSPSP用商品マスタファイル生成処理を行う。
REM version 1.00 2011/02/09 S.Umemoto:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== KSPSP用商品マスタファイル生成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB201000
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
