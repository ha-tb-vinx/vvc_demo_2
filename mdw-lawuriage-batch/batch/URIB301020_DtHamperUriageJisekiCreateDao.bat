REM @echo off
REM ============================================================================
REM タイトル：ハンパー売上実績データ作成（卸）
REM 説明    ：ハンパー売上実績を作成する。
REM version 1.00 2017/08/08 X.Liu:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB301020
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
